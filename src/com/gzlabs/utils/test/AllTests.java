package com.gzlabs.utils.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCryptoUtils.class, TestDateUtils.class,
		TestICalUtils.class, TestSSHUtils.class, TestWidgetUtilities.class })
public class AllTests {

}
