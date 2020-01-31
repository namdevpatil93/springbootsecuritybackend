package com.sts.springbootsecuritybackend.util;

/**
 * Applicant constants.
 */
public final class Constants {

    private Constants() {
    }

    // Spring profile for development, production and test
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_TEST = "test";

    public static final String SPRING_PROFILE_DEFAULT = SPRING_PROFILE_DEVELOPMENT;

    public static final String ADMIN_LOGIN_URL = "/adminLogin";
    public static final String APPLICANT_LOGIN_URL = "/";
    public static final String DASHBOARD_URL = "/dashboard";

    public static final String BASE_UPLOAD_DIR = "uploads";
    public static final String BASE_DOWNLOAD_DIR = "downloads";

    public static final String APPLICANT_PHOTO_UPLOAD_BASE_DIR = "photos";
    public static final String APPLICANT_SIGNATURE_UPLOAD_BASE_DIR = "signatures";
    public static final String CHALLANS_UPLOAD_BASE_DIR = "challans";
    public static final String UNDERTAKING_BASE_DIR = "undertakings";

    public static final String EXPERIENCE_LETTER_DIR = "ExperienceLetter";
    
    public static final String ADMIN_DIR = "admin";
    public static final String ADMITCARD_PATH = "admitcards";
    public static final String DOCUMENT_DIR = "documents";
    
    public static final String QUERY_DIR = "QueryAttachments";
    public static final String REPORTS_BASE_DIR = "reports";
    
    public static final String HDFC_LOGO_PATH = "/static/app/images/HDFCLogo.jpg";
    public static final String FONT_FILE_PATH = "/static/app/fonts/FreeSans.ttf";
    public static final String ITEXT_LICENSE_FILE_PATH = "/static/app/itext/itextkey-0.xml";
    	
    public static final String BANK_CHALAN_SEND_DAILY_PATH ="/static/app";
    
    public static final String DOWNLOAD_APPLICANT_ATTACHMENT = "downloadApplicantAttachment";
    
    public static final String CHALLAN_SEND_AS_EMAIL ="challans";
    
    public static final String REPORTS_TO_MAIL="reportsToMail";
    
    public static final String QUALIFICATION_UPLOAD="qualificationBulkUpload";
    
    public static final String ANSWER_SHEET ="answerSheets";
    
    public static final String ONLINE_QUERY_API_BASE_DIR = "bill_desk_online_query_api";
    
    public static final String PREFERRED_CITY_DIR = "preferred_city";
}
