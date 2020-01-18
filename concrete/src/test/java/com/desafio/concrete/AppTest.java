package com.desafio.concrete;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestsHttpStatus.class, TestsLogin.class})
public class AppTest {

}