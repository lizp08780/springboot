package com.lizp.springboot.util;

import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {
	private int dbSyncPeriod = 1 * 60;
	private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";
	@Autowired
	private OnlineSessionFactory onlineSessionFactory;

}
