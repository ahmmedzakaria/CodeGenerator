package serviceAndDao;

import java.util.ArrayList;
import java.util.List;


import sqltojava.model.JavaAnnotation;
class UUID{}
public class TestModel {
	private String fieldName;
    private String sqlFieldName;
    private String dataType;
    private List<JavaAnnotation> annotations;
    private String rightComment;
    private String topComment;
    private String importStr;
    private UUID clientId;
    private boolean customerAuthselectBio;
    private boolean customerAuthOtp;
    private boolean isOtpRequired;
    private int absBillsAutonumLength;
    private boolean commissionForCrbillpayNo; 
    private double commissionPerCrbillpay;
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<JavaAnnotation> getAnnotatios() {
        return annotations;
    }

    public void setAnnotations(List<JavaAnnotation> annotatios) {
        this.annotations = annotatios;
    }

    public String getRightComment() {
        return rightComment;
    }

    public void setRightComment(String rightComment) {
        this.rightComment = rightComment;
    }

    public String getTopComment() {
        return topComment;
    }

    public void setTopComment(String topComment) {
        this.topComment = topComment;
    }

    public String getSqlFieldName() {
        return sqlFieldName;
    }

    public void setSqlFieldName(String sqlFieldName) {
        this.sqlFieldName = sqlFieldName;
    }


	public String getImportStr() {
		return importStr;
	}

	public void setImportStr(String importStr) {
		this.importStr = importStr;
	}

	public UUID getClientId() {
		return clientId;
	}

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}

	public boolean isCustomerAuthselectBio() {
		return customerAuthselectBio;
	}

	public void setCustomerAuthselectBio(boolean customerAuthselectBio) {
		this.customerAuthselectBio = customerAuthselectBio;
	}

	public boolean isCustomerAuthOtp() {
		return customerAuthOtp;
	}

	public void setCustomerAuthOtp(boolean customerAuthOtp) {
		this.customerAuthOtp = customerAuthOtp;
	}

	public boolean isOtpRequired() {
		return isOtpRequired;
	}

	public void setOtpRequired(boolean isOtpRequired) {
		this.isOtpRequired = isOtpRequired;
	}

	public int getAbsBillsAutonumLength() {
		return absBillsAutonumLength;
	}

	public void setAbsBillsAutonumLength(int absBillsAutonumLength) {
		this.absBillsAutonumLength = absBillsAutonumLength;
	}

	public boolean isCommissionForCrbillpayNo() {
		return commissionForCrbillpayNo;
	}

	public void setCommissionForCrbillpayNo(boolean commissionForCrbillpayNo) {
		this.commissionForCrbillpayNo = commissionForCrbillpayNo;
	}

	public double getCommissionPerCrbillpay() {
		return commissionPerCrbillpay;
	}

	public void setCommissionPerCrbillpay(double commissionPerCrbillpay) {
		this.commissionPerCrbillpay = commissionPerCrbillpay;
	}

	public List<JavaAnnotation> getAnnotations() {
		return annotations;
	}
	
	
}
