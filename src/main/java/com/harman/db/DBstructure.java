package com.harman.db;

import java.sql.Connection;

import com.harman.Model.AppModel.DataModel;
import com.harman.utils.ErrorType;

public interface DBstructure {


	public ErrorType insertDeviceModel(DataModel mHarmanDeviceModel, Connection conn);

}
