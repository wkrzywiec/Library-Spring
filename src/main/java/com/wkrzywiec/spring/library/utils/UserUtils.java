package com.wkrzywiec.spring.library.utils;

import java.util.HashMap;
import java.util.List;

import com.wkrzywiec.spring.library.entity.User;

public interface UserUtils {

	public HashMap<String, String> userRolesInString(List<User> userList);
}
