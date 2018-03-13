package com.harman.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import com.harman.Model.AppModel.AccountModel;
import com.harman.Model.AppModel.DataModel;
import com.harman.utils.ErrorType;

public class DBManager implements DBstructure, DbConstant {

	static DBManager mariaModel;

	public static DBManager getInstance() {
		if (mariaModel == null)
			mariaModel = new DBManager();
		return mariaModel;
	}

	Connection connn = null;

	public Connection openConnection(String database) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			connn = DriverManager.getConnection("jdbc:mariadb://localhost/" + database, "root", "");
			System.out.println("Connected database successfully...");
		} catch (SQLException e) {
			System.out.println("Failed to connect db");
		} catch (Exception e) {
			System.out.println("Failed to connect db");
		}
		return connn;
	}

	public void closeConnection() {
		try {
			if (connn != null) {
				connn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public Object getListOfUserAccounts(String userID) {

		String query = "select  from " + userDetail + " where " + userID + " = " + "'" + DBManager.userID + "'";
		Vector<AccountModel> mAccList = new Vector<>();
		ErrorType response = ErrorType.NO_ERROR;
		Statement stmt = null;
		try {
			Connection connection = DBManager.getInstance().openConnection("hash");
			stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			resultSet.last();
			if (resultSet.getRow() == 0) {
				return mAccList;
			} else {
				do {
					AccountModel accountModel = new AccountModel();
					String va_email = resultSet.getString(ud_va_accessToken);
					String va_accessToken = resultSet.getString(ud_va_accessToken);
					accountModel.setAccessToken(va_accessToken);
					accountModel.setEmail(va_email);
					mAccList.add(accountModel);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			response = ErrorType.ERROR_ACCESSING_DB;
		} catch (Exception e) {
			response = ErrorType.DB_CONNECTION_ERROR;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se) {
				response = ErrorType.ERROR_CLOSING_DB;
				System.out.println("SQLException while closing data");
			}
		}
		if (mAccList.size() == 0)
			return response;
		else
			return mAccList;
	}

	public ErrorType insertDeviceModel(DataModel mHarmanDeviceModel, Connection conn) {
		return null;
		/*
		 * ErrorType response = ErrorType.NO_ERROR; Statement stmt = null; try {
		 * stmt = conn.createStatement(); String query = "select  from " +
		 * harmanDevice + " where " + macAddress + " = " + "'" +
		 * mHarmanDeviceModel.getMacAddress() + "'"; ResultSet ifExistsResponse
		 * = stmt.executeQuery(query); ifExistsResponse.last(); if
		 * (ifExistsResponse.getRow() == 0) { try { String queryInsertNewRow =
		 * "INSERT INTO " + harmanDevice + "(" + macAddress + "," + productId +
		 * "," + colorId + "," + productName + "," + colorName + "," +
		 * FirmwareVersion + "," + AppVersion + ") VALUE ('" +
		 * mHarmanDeviceModel.getMacAddress() + "','" +
		 * mHarmanDeviceModel.getProductId() + "','" +
		 * mHarmanDeviceModel.getColorId() + "','" +
		 * mHarmanDeviceModel.getProductName() + "','" +
		 * mHarmanDeviceModel.getColorName() + "','" +
		 * mHarmanDeviceModel.getFirmwareVersion() + "','" +
		 * mHarmanDeviceModel.getAppVersion() + "')"; int result =
		 * stmt.executeUpdate(queryInsertNewRow); if (result == 0) response =
		 * ErrorType.ERROR_INSERTING_DB; } catch (SQLException se) { response =
		 * ErrorType.ERROR_INSERTING_DB; } } else { try { String queryUpdate =
		 * "update " + harmanDevice + " set " + FirmwareVersion + "= '" +
		 * mHarmanDeviceModel.getFirmwareVersion() + "'," + AppVersion + " = '"
		 * + mHarmanDeviceModel.getAppVersion() + "' where " + macAddress +
		 * " = '" + mHarmanDeviceModel.getMacAddress() + "'"; int result =
		 * stmt.executeUpdate(queryUpdate); if (result == 0) response =
		 * ErrorType.ERROR_UPDATING_DB; } catch (SQLException se) { response =
		 * ErrorType.ERROR_UPDATING_DB; } } } catch (Exception e) { response =
		 * ErrorType.NETWORK_NOT_AVAILBLE; } finally { try { if (stmt != null) {
		 * stmt.close(); } } catch (SQLException se) { response =
		 * ErrorType.ERROR_CLOSING_DB; System.out.println(
		 * "SQLException while closing data"); } } return response;
		 */
	}

	@SuppressWarnings("rawtypes")
	public StringBuffer createQuery(LinkedHashMap<String, Integer> keyValueMap, String table, String macAddress) {
		Iterator<Entry<String, Integer>> it = keyValueMap.entrySet().iterator();
		StringBuffer queryBuffer = new StringBuffer();

		StringBuffer bufferKey = new StringBuffer();
		StringBuffer bufferValue = new StringBuffer();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (it.hasNext()) {
				bufferKey.append(pair.getKey() + ",");
				bufferValue.append(pair.getValue() + ",");
			} else {
				bufferKey.append(pair.getKey());
				bufferValue.append(pair.getValue());
			}
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		queryBuffer.append("INSERT INTO " + table + "(harmanDevice_Id," + bufferKey + ") VALUE ( '" + macAddress + "',"
				+ bufferValue + " )");

		System.out.println(queryBuffer);
		return queryBuffer;
	}

}
