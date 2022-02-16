package com.epam.finalproject.dao.impl;

import com.epam.finalproject.dao.ConnectionPool;
import com.epam.finalproject.dao.InvoiceDao;
import com.epam.finalproject.dao.exception.DaoException;
import com.epam.finalproject.model.Invoice;
import com.epam.finalproject.model.InvoiceStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDaoImpl implements InvoiceDao {
    private  static  final String SQL_CREATE_INVOICE = "INSERT INTO invoices (invoice_code,user_id, "+
            "status_id,invoice_date, invoice_notes)" +
            "VALUES (?,?,?,?,?)";
    private static final String SQL_GET_BY_ID_INVOICE = "SELECT * FROM invoices JOIN invoice_status ON invoices.status_id = invoice_status.status_id  WHERE  invoice_id = ?";
    private static final String SQL_UPDATE_INVOICE = "UPDATE invoices SET invoice_code = ?," +
            "user_id = ?, status_id = ?,"+
            "invoice_date = ?, invoice_notes = ? WHERE invoice_id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM invoices WHERE invoice_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM invoices JOIN invoice_status ON invoices.status_id = invoice_status.status_id ";
    private static final String SQL_SELECT_OFFSET = "SELECT * FROM invoices JOIN invoice_status ON invoices.status_id = invoice_status.status_id  ORDER BY invoice_id LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_COUNT = "SELECT COUNT(invoice_id) AS count FROM invoices";
    private static final String SQL_DELETE_BY_CODE = "DELETE FROM invoices WHERE invoice_code = ?";
    private static final Logger log = Logger.getLogger(InvoiceDaoImpl.class);



    @Override
    public void create(Invoice invoice) {
        log.info("Enter create:\n " + invoice);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(SQL_CREATE_INVOICE)) {
            insertStatement.setLong(1,invoice.getInvoiceCode());
            insertStatement.setInt(2,invoice.getUserId());
            insertStatement.setInt(3,invoice.getStatus().ordinal());
            insertStatement.setTimestamp(4, Timestamp.from(invoice.getDate().toInstant()) );
            insertStatement.setString(5,invoice.getInvoiceNotes());
            insertStatement.execute();
        } catch (SQLException e) {
            log.error("Can't create invoice!\n=======");
            throw new DaoException("Can`t create invoice", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public Invoice getById(int id) {
        log.info("Enter getById invoiceId: "+id);
        Invoice invoice = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(SQL_GET_BY_ID_INVOICE)) {
            insertStatement.setInt(1, id);
            try (ResultSet resultSet = insertStatement.executeQuery()) {
                while (resultSet.next()) {
                    invoice = new Invoice();
                    invoice.setInvoiceId(resultSet.getInt("invoice_id"));
                    invoice.setInvoiceCode(resultSet.getLong("invoice_code"));
                    invoice.setUserId(resultSet.getInt("user_id"));
                    invoice.setStatus(InvoiceStatus.valueOf(resultSet.getString("status_description")));
                    invoice.setDate(resultSet.getTimestamp("invoice_date"));
                    invoice.setInvoiceNotes(resultSet.getString("invoice_notes"));
                }
            }

        } catch (SQLException e) {
            log.error("Can`t get invoice");
            throw new DaoException("Can`t get invoice", e);
        }
        log.info("Done!\n=======");
        return invoice;

    }

    @Override
    public void deleteByCode(Long code) {
        log.info("Enter deleteByCode invoiceCode: " + code);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_CODE)) {
            statement.setLong(1, code);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can`t delete invoice");
            throw new DaoException("Can`t delete invoice", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public void update(Invoice invoice) {
        log.info("Enter update invoice:\n " + invoice);
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement insertStatement = connection.prepareStatement(SQL_UPDATE_INVOICE)) {
                insertStatement.setLong(1,invoice.getInvoiceCode());
                insertStatement.setInt(2,invoice.getUserId());
                insertStatement.setInt(3,invoice.getStatus().ordinal());
                insertStatement.setTimestamp(4,Timestamp.from(invoice.getDate().toInstant()) );
                insertStatement.setString(5,invoice.getInvoiceNotes());
                insertStatement.setInt(6,invoice.getInvoiceId());
                insertStatement.executeUpdate();
                connection.setAutoCommit(false);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                connection.rollback();
                log.error("Can`t update invoice");
                throw new DaoException("Can`t update invoice", e);
            }
        }
        catch (SQLException e) {
            log.error("Can`t update invoice");
            throw new DaoException("Can`t update invoice", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public void deleteById(int id) {
        log.info("Enter deleteById invoiceId: " + id);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can`t delete invoice");
            throw new DaoException("Can`t delete invoice", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public List<Invoice> findAll() {
        log.info("Enter findAll invoices:");
        List<Invoice> invoices  = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(resultSet.getInt("invoice_id"));
                invoice.setInvoiceCode(resultSet.getLong("invoice_code"));
                invoice.setUserId(resultSet.getInt("user_id"));
                invoice.setStatus(InvoiceStatus.valueOf(resultSet.getString("status_description")));
                invoice.setDate(resultSet.getTimestamp("invoice_date"));
                invoice.setInvoiceNotes(resultSet.getString("invoice_notes"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            log.error("Can`t find all invoices");
            throw new DaoException("Can`t find all invoices", e);
        }
        log.info("Done!\n=======");
        return invoices;
    }

    @Override
    public List<Invoice> findInvoicesUsingLimitAndOffset(int currentPage, int recordsPerPage) {
        log.info("Enter findInvoicesUsingLimitAndOffset invoices: ");
        List<Invoice> invoices  = new ArrayList<>();
        int start = (currentPage) * recordsPerPage - recordsPerPage;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_OFFSET)) {
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, start);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(resultSet.getInt("invoice_id"));
                invoice.setInvoiceCode(resultSet.getLong("invoice_code"));
                invoice.setUserId(resultSet.getInt("user_id"));
                invoice.setStatus(InvoiceStatus.valueOf(resultSet.getString("status_description")));
                invoice.setDate(resultSet.getTimestamp("invoice_date"));
                invoice.setInvoiceNotes(resultSet.getString("invoice_notes"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            log.error("Can`t find invoices with filters");
            throw new DaoException("Can`t find invoices with filters", e);
        }
        log.info("Done!\n=======");
        return invoices;
    }

    @Override
    public int getNumberOfRows() {
        log.info("Enter getNumberOfRows invoices:");
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numOfRows = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            log.error("Can`t get numbers of rows invoices");
            throw new DaoException("Can`t get numbers of rows invoices", e);
        }
        log.info("Done!\n=======");
        return numOfRows;
    }
}
