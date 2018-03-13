package com.harman.db;

public interface DbConstant {

	public String harmanDevice = "harmanDevice";
	public String macAddress = "macAddress", productId = "productId", colorId = "colorId", productName = "productName",
			colorName = "colorName", FirmwareVersion = "FirmwareVersion", AppVersion = "AppVersion";

	public String DeviceAnalytics = "DeviceAnalytics";
	public String BoomboxDeviceAnalytics = "BoomboxDeviceAnalytics";
	public String JBLXtremeCLDeviceAnalytics = "JBLXtremeCLDeviceAnalytics";

	public String Broadcaster = "Broadcaster", Receiver = "Receiver",
			CriticalTemperatureShutDown = "CriticalTemperatureShutDown", PowerOnOffCount = "PowerOnOffCount",
			EQSettings = "EQSettings", PowerBankUsage = "PowerBankUsage";

	public String AppAnalytics = "AppAnalytics";
	public String BoomboxAppAnalytics = "BoomboxAppAnalytics";
	public String JBLXtremeCLAppAnalytics = "JBLXtremeCLAppAnalytics";

	public String SpeakerMode_Stereo = "SpeakerMode_Stereo", SpeakerMode_Party = "SpeakerMode_Party",
			SpeakerMode_Single = "SpeakerMode_Single";
	public String AppToneToggle = "AppToneToggle", AppMFBMode = "AppMFBMode";
	public String AppHFPToggle = "AppHFPToggle", AppEQMode = "AppEQMode";

	final public String userDetail = "UserDetail", ud_va_email = "va_email", ud_va_accessToken = "va_accessToken",
			userID = "userID";

}
