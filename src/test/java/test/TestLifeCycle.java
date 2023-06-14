package test;

import org.junit.jupiter.api.*;

public class TestLifeCycle {



    @BeforeAll
    static void beforeAll(){
        System.out.println("## beforeAll 호출");
        System.out.println();
    }
    @AfterAll
    static void AfterAll(){
        System.out.println("## AfterAll 호출");
        System.out.println();
    }
    @BeforeEach
     void beforeEach(){
        System.out.println("## BeforeEach 호출");
        System.out.println();
    }
    @AfterEach
     void afterEach(){
        System.out.println("## beforeAll 호출");
        System.out.println();
    }

    @Test
    void test1(){
    //given
        System.out.println("##test1 시작 ##");
    //when
        System.out.println();
    //then
    }

    @Test
    @DisplayName("test case 2")
    void test2(){
    //given
        System.out.println("## test2 시작##");
    //when
        System.out.println();
    //then
    }

    @Test
    @Disabled
    void test3(){
    //given
        System.out.println("## test3 시작");
    //when
        System.out.println();
    //then
    }

}
