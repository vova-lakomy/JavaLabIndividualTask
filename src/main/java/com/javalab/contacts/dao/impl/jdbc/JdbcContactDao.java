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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            e.printStackTrace();
        } finally {
            try {
                if (getByDayAndMonthStatement != null) {
                    getByDayAndMonthStatement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            try {
                if (statementGetContact != null) {
                    statementGetContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
        return resultObject.getId() != null ? resultObject : null;
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
                e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            try {
                if (searchStatement != null) {
                    searchStatement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            try {
                if (statementSaveContact != null) {
                    statementSaveContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
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
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (statementDeleteContact != null) {
                    statementDeleteContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
    }

    private Contact createContactFromResultSet(ResultSet resultSet, boolean joinAttachments) throws SQLException {
        logger.debug("creating 'Contact' entity from {}", resultSet.getClass().getName());

        ContactAddress address = new ContactAddress();
        address.setCountry(resultSet.getString("country"));
        address.setTown(resultSet.getString("town"));
        address.setStreet(resultSet.getString("street"));
        address.setHouseNumber(resultSet.getInt("house_number"));
        address.setFlatNumber(resultSet.getInt("flat_number"));
        address.setZipCode(resultSet.getInt("zip_code"));

        Contact resultObject = new Contact();
        resultObject.setId(resultSet.getInt("id"));
        resultObject.setFirstName(resultSet.getString("first_name"));
        resultObject.setSecondName(resultSet.getString("second_name"));
        resultObject.setLastName(resultSet.getString("last_name"));
        resultObject.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
        resultObject.setSex(Sex.valueOf(resultSet.getString("sex")));
        resultObject.setNationality(resultSet.getString("nationality"));
        resultObject.setMartialStatus(MartialStatus.valueOf(resultSet.getString("martial_status")));
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
        ContactAddress address = contact.getContactAddress();
        statement.setString(1, contact.getFirstName());
        statement.setString(2, contact.getSecondName());
        statement.setString(3, contact.getLastName());
        statement.setString(4, contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        statement.setString(5, contact.getSex().name());
        statement.setString(6, contact.getNationality());
        statement.setString(7, contact.getMartialStatus().name());
        statement.setString(8, contact.getWebSite());
        statement.setString(9, contact.geteMail());
        statement.setString(10, contact.getCurrentJob());
        statement.setString(11, contact.getPhotoLink());
        statement.setString(12, address.getCountry());
        statement.setString(13, address.getTown());
        statement.setString(14, address.getStreet());
        statement.setInt(15, address.getHouseNumber());
        statement.setInt(16, address.getFlatNumber());
        statement.setInt(17, address.getZipCode());
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
                    "e_mail, current_job, photo_link, country, town, street, house_number, flat_number, zip_code) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        } else {
            return "UPDATE contact SET " +
                    "first_name=?, second_name=?, last_name=?, date_of_birth=?, sex=?, nationality=?, martial_status=?, " +
                    "web_site=?, e_mail=?, current_job=?, photo_link=?, country=?, town=?, street=?, house_number=?, " +
                    "flat_number=?, zip_code=? WHERE id=" + contactId;
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
