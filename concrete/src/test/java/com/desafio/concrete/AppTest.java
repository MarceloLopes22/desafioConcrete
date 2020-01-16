package com.desafio.concrete;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestsHttpStatus.class,
	TestsLogin.class
})
public class AppTest {

}