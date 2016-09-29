package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.Sex;
import com.javalab.contacts.util.CustomReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase;

public class JdbcContactDao implements ContactDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcContactDao.class);
    private int rowsPerPageCount = 10;
    private int numberOfRecordsFound = 0;
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private PhoneNumberDao phoneNumberDao = new JdbcPhoneNumberDao();
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();
    private static final boolean JOIN_ATTACHMENTS_TRUE = true;
    private static final boolean JOIN_ATTACHMENTS_FALSE = false;

    @Override
    public Contact get(Integer contactId) {
        return getContactFromDB(contactId, JOIN_ATTACHMENTS_TRUE);
    }

    @Override
    public Collection<Contact> getByDayAndMonth(Integer day, Integer month){
        logger.debug("try to get contacts by day - {} and month - {}",day,month);
        PreparedStatement getByDayAndMonthStatement = null;

        Collection<Contact> resultCollection = new ArrayList<>();
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            getByDayAndMonthStatement = connection
                    .prepareStatement("SELECT * FROM contact WHERE MONTH(date_of_birth) = ? " +
                                                              "AND DAY(date_of_birth) = ? ORDER BY last_name");
            getByDayAndMonthStatement.setInt(1, month);
            getByDayAndMonthStatement.setInt(2, day);
            ResultSet resultSet = getByDayAndMonthStatement.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createContactFromResultSet(resultSet, JOIN_ATTACHMENTS_FALSE));
            }
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}",e);
        } finally {
            try {
                if (getByDayAndMonthStatement != null) {
                    getByDayAndMonthStatement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
        return resultCollection;
    }

    @Override
    public Contact getContactShortInfo(Integer contactId) {
        return getContactFromDB(contactId, JOIN_ATTACHMENTS_FALSE);
    }

    private Contact getContactFromDB(Integer id, Boolean fullInfo) {
        logger.debug("search for contact with id - " + id);
        PreparedStatement statementGetContact = null;
        Contact resultObject = new Contact();
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementGetContact = connection.prepareStatement("SELECT * FROM contact WHERE id= ?");
            statementGetContact.setInt(1, id);
            ResultSet resultSet = statementGetContact.executeQuery();
            while (resultSet.next()) {
                resultObject = createContactFromResultSet(resultSet, fullInfo);
            }
            resultSet.close();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}",e);
        } finally {
            try {
                if (statementGetContact != null) {
                    statementGetContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
        return resultObject.getId() != null ? resultObject : null;      // FIXME: 29.09.16
    }

    @Override
    public Collection<Contact> getContactList(int pageNumber) {
        logger.debug("try to get contacts list");
        PreparedStatement statementGetContactList = null;

        Collection<Contact> resultCollection = new ArrayList<>();
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementGetContactList = connection
                    .prepareStatement("SELECT SQL_CALC_FOUND_ROWS * FROM contact ORDER BY last_name LIMIT ?,?");
            statementGetContactList.setInt(1, rowsPerPageCount * pageNumber);
            statementGetContactList.setInt(2, rowsPerPageCount);

            ResultSet resultSet = statementGetContactList.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createContactFromResultSet(resultSet, JOIN_ATTACHMENTS_FALSE));
            }
            connection.commit();
            logger.debug("closed transaction");
            numberOfRecordsFound = getNumberOfRecordsFound(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementGetContactList != null) {
                    statementGetContactList.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
        return resultCollection;
    }

    @Override
    public Collection<Contact> search(ContactSearchDTO searchObject, int pageNumber) {
        PreparedStatement searchStatement = null;
        String searchQueryString = defineSearchQueryString(searchObject);
        if (pageNumber >= 0){
            searchQueryString = searchQueryString + " LIMIT ?, ?;";
        }
        Collection<Contact> resultCollection = new ArrayList<>();
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            searchStatement = connection.prepareStatement(searchQueryString);
            if (pageNumber >= 0){
                searchStatement.setInt(1, rowsPerPageCount * pageNumber);
                searchStatement.setInt(2, rowsPerPageCount);
            }
            ResultSet resultSet = searchStatement.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createContactFromResultSet(resultSet, false));
            }
            connection.commit();
            logger.debug("closed transaction");
            numberOfRecordsFound = getNumberOfRecordsFound(connection);
        } catch (SQLException e) {
            logger.error("{}",e);
        } finally {
            try {
                if (searchStatement != null) {
                    searchStatement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
        return resultCollection;
    }

    @Override
    public Integer save(Contact contact) {
        logger.debug("saving contact with id= " + contact.getId());
        String saveContactQuery = defineSaveQueryString(contact.getId());
        PreparedStatement statementSaveContact = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementSaveContact = connection.prepareStatement(saveContactQuery, Statement.RETURN_GENERATED_KEYS);
            setSaveStatementParams(statementSaveContact, contact);
            statementSaveContact.executeUpdate();

            if (contact.getId() == null) {
                contact.setId(getLastGeneratedValue(statementSaveContact));
            }

            if (contact.getPhoneNumbers() != null) {
                contact.getPhoneNumbers().forEach(phoneNumber ->
                        phoneNumberDao.save(phoneNumber, contact.getId(), connection));
            }
            if (contact.getAttachments() != null) {
                contact.getAttachments().forEach(attachment ->
                        attachmentDao.save(attachment, contact.getId(), connection));
            }
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}",e);
        } finally {
            try {
                if (statementSaveContact != null) {
                    statementSaveContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
        return contact.getId();
    }

    @Override
    public void delete(Integer id) {
        logger.debug("deleting contact with id= " + id);
        PreparedStatement statementDeleteContact = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementDeleteContact = connection.prepareStatement("DELETE FROM contact WHERE id= ?");
            statementDeleteContact.setInt(1, id);
            statementDeleteContact.executeUpdate();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            try {
                logger.debug("transaction rolled back");
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("{}",e1);
            }
            logger.error("{}",e);
        } finally {
            try {
                if (statementDeleteContact != null) {
                    statementDeleteContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
    }

    @Override
    public String getPersonalLink(Integer id) {
        logger.debug("looking for personal link for contact with id= " + id);
        PreparedStatement statementGetPersonalLink = null;
        Connection connection = connectionManager.receiveConnection();
        String personalLink = null;
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementGetPersonalLink = connection.prepareStatement("SELECT personal_link FROM contact WHERE id= ?");
            statementGetPersonalLink.setInt(1, id);
            ResultSet resultSet = statementGetPersonalLink.executeQuery();
            while (resultSet.next()) {
                personalLink = resultSet.getString("personal_link");
            }
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            try {
                logger.debug("transaction rolled back");
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("{}",e1);
            }
            logger.error("{}",e);
        } finally {
            try {
                if (statementGetPersonalLink != null) {
                    statementGetPersonalLink.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
        return personalLink;
    }

    @Override
    public void setPersonalLink(String personalLink, Integer id){
        logger.debug("trying to set personal link for contact with id= " + id);
        PreparedStatement statementSetPersonalLink = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementSetPersonalLink = connection.prepareStatement("UPDATE contact SET personal_link = ? WHERE id= ?");
            statementSetPersonalLink.setString(1, personalLink);
            statementSetPersonalLink.setInt(2, id);
            statementSetPersonalLink.executeUpdate();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            try {
                logger.debug("transaction rolled back");
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("{}",e1);
            }
            logger.error("{}",e);
        } finally {
            try {
                if (statementSetPersonalLink != null) {
                    statementSetPersonalLink.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}",e);
            }
            connectionManager.putBackConnection(connection);
        }
    }

    private Contact createContactFromResultSet(ResultSet resultSet, boolean joinAttachments) throws SQLException {
        logger.debug("creating 'Contact' entity from {}", resultSet.getClass().getName());
        Date date = resultSet.getDate("date_of_birth");
        LocalDate dateOfBirth = null;
        if (date != null) {
            dateOfBirth = date.toLocalDate();
        }
        String sexString = resultSet.getString("sex");
        Sex sex = null;
        if (sexString != null) {
            sex = Sex.valueOf(sexString);
        }
        String martialStatusString = resultSet.getString("martial_status");
        MartialStatus martialStatus = null;
        if (martialStatusString != null) {
            martialStatus = MartialStatus.valueOf(martialStatusString);
        }
        ContactAddress address = new ContactAddress();
        address.setCountry(resultSet.getString("country"));
        address.setTown(resultSet.getString("town"));
        address.setStreet(resultSet.getString("street"));
        address.setHouseNumber((Integer) resultSet.getObject("house_number"));
        address.setFlatNumber((Integer) resultSet.getObject("flat_number"));
        address.setZipCode((Integer) resultSet.getObject("zip_code"));

        Contact resultObject = new Contact();
        resultObject.setId(resultSet.getInt("id"));
        resultObject.setFirstName(resultSet.getString("first_name"));
        resultObject.setSecondName(resultSet.getString("second_name"));
        resultObject.setLastName(resultSet.getString("last_name"));
        resultObject.setDateOfBirth(dateOfBirth);
        resultObject.setSex(sex);
        resultObject.setNationality(resultSet.getString("nationality"));
        resultObject.setMartialStatus(martialStatus);
        resultObject.setWebSite(resultSet.getString("web_site"));
        resultObject.seteMail(resultSet.getString("e_mail"));
        resultObject.setCurrentJob(resultSet.getString("current_job"));
        resultObject.setPhotoLink(resultSet.getString("photo_link"));
        resultObject.setContactAddress(address);
        if (joinAttachments) {
            resultObject.setPhoneNumbers(phoneNumberDao.getByContactId(resultObject.getId()));
            resultObject.setAttachments(attachmentDao.getByContactId(resultObject.getId()));
        }
        logger.debug("'Contact' entity created");
        return resultObject;
    }

    private void setSaveStatementParams(PreparedStatement statement, Contact contact) throws SQLException {
        logger.debug("setting params to {} ", statement);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = contact.getDateOfBirth();
        String dateOfBirthString = null;
        if (dateOfBirth != null) {
            dateOfBirthString = dateOfBirth.format(formatter);
        }
        Sex sex = contact.getSex();
        String sexString = null;
        if (sex != null) {
            sexString = sex.name();
        }
        MartialStatus martialStatus = contact.getMartialStatus();
        String martialStatusString = null;
        if (martialStatus != null) {
            martialStatusString = martialStatus.name();
        }
        ContactAddress address = contact.getContactAddress();
        statement.setString(1, contact.getFirstName());
        statement.setString(2, contact.getSecondName());
        statement.setString(3, contact.getLastName());
        statement.setString(4, dateOfBirthString);
        statement.setString(5, sexString);
        statement.setString(6, contact.getNationality());
        statement.setString(7, martialStatusString);
        statement.setString(8, contact.getWebSite());
        statement.setString(9, contact.geteMail());
        statement.setString(10, contact.getCurrentJob());
        statement.setString(11, contact.getPhotoLink());
        statement.setString(12, address.getCountry());
        statement.setString(13, address.getTown());
        statement.setString(14, address.getStreet());
        statement.setObject(15, address.getHouseNumber());
        statement.setObject(16, address.getFlatNumber());
        statement.setObject(17, address.getZipCode());
        statement.setString(18, contact.getPersonalLink());
        logger.debug("setting params to {} done", statement);
    }

    private Integer getLastGeneratedValue(PreparedStatement statement) throws SQLException {
        Integer lastGeneratedValue = null;
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            lastGeneratedValue = generatedKeys.getInt(1);
        }
        generatedKeys.close();
        logger.debug("got last generated value - {}", lastGeneratedValue);
        return lastGeneratedValue;
    }

    private Integer getNumberOfRecordsFound(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
        Integer recordCount = null;
        if (resultSet.next()) {
            recordCount = resultSet.getInt(1);
        }
        return recordCount;
    }

    private String defineSaveQueryString(Integer contactId) {
        logger.debug("defining save query string");
        if (contactId == null) {
            return "INSERT INTO contact " +
                    "(first_name, second_name, last_name, date_of_birth, sex, nationality, martial_status, web_site, " +
                    "e_mail, current_job, photo_link, country, town, street, house_number, flat_number, zip_code, personal_link) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        } else {
            return "UPDATE contact SET " +
                    "first_name=?, second_name=?, last_name=?, date_of_birth=?, sex=?, nationality=?, martial_status=?, " +
                    "web_site=?, e_mail=?, current_job=?, photo_link=?, country=?, town=?, street=?, house_number=?, " +
                    "flat_number=?, zip_code=?, personal_link=? WHERE id=" + contactId;
        }
    }

    private String defineSearchQueryString(ContactSearchDTO searchObject) {
        Map<String, Object> objectFieldValues = CustomReflectionUtil.getObjectFieldsWithValues(searchObject);
        StringBuilder query = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM contact WHERE TRUE ");
        String orderBy = null;
        for (Map.Entry<String, Object> mapEntry : objectFieldValues.entrySet()){
            String fieldName = mapEntry.getKey();
            Object fieldValue = mapEntry.getValue();
            if (fieldName.equals("orderBy")) {
                if (isNotBlank((String) fieldValue)) {
                    orderBy = convertFieldNameToTableName((String) fieldValue);
                }
            } else if (fieldName.contains("GreaterThan")) {
                fieldName = fieldName.replace("GreaterThan","");
                if (isNotBlank((String) fieldValue)){
                    query.append(" AND ");
                    query.append(convertFieldNameToTableName(fieldName));
                    query.append(" > '");
                    query.append(fieldValue);
                    query.append("' ");
                }
            } else if (fieldName.contains("LessThan")) {
                fieldName = fieldName.replace("LessThan","");
                if (isNotBlank((String) fieldValue)){
                    query.append(" AND ");
                    query.append(convertFieldNameToTableName(fieldName));
                    query.append(" < '");
                    query.append(fieldValue);
                    query.append("' ");
                }
            } else if (isNotBlank((String) fieldValue)) {
                query.append(" AND ");
                query.append(convertFieldNameToTableName(fieldName));
                query.append(" LIKE '");
                query.append(fieldValue);
                query.append("%'");
            }
        }
        if (orderBy != null) {
            query.append(" ORDER BY ");
            query.append(orderBy);
        }
//        query.append(" LIMIT ?, ?;");
        return query.toString();
    }

    private String convertFieldNameToTableName(String fieldName) {
        String[] words = splitByCharacterTypeCamelCase(fieldName);
        StringBuilder tableName = new StringBuilder();
        if (words.length == 1) {
            tableName.append(words[0]);
        } else {
            for (String word : words) {
                tableName.append(word.toLowerCase());
                tableName.append('_');
            }
            tableName.setLength(tableName.length() - 1);
        }
        return tableName.toString();
    }

    @Override
    public int getRowsPerPageCount() {
        return rowsPerPageCount;
    }

    @Override
    public void setRowsPerPageCount(int rowsCount) {
        this.rowsPerPageCount = rowsCount;
    }

    @Override
    public int getNumberOfRecordsFound() {
        return numberOfRecordsFound;
    }
}
