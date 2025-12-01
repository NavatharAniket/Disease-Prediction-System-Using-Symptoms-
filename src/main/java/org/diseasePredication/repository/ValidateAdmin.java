package org.diseasePredication.repository;

import org.diseasePredication.model.AdminLogin;

public interface ValidateAdmin {
	boolean verifyAdminLogin(AdminLogin login);
}
