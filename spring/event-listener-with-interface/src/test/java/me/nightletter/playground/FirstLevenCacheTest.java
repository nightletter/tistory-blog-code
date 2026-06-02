package me.nightletter.playground;

import jakarta.transaction.Transactional;
import me.nightletter.playground.domain.Member;
import me.nightletter.playground.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.beans.Transient;

@SpringBootTest
public class FirstLevenCacheTest {

    @Autowired
    MemberRepository sut;

    protected String TEST_EMAIl = "test@email.com";
    protected Long TEST_ID;

//    @BeforeEach
//    public void setup() {
//        Member save = sut.save(
//                new Member(TEST_EMAIl, "pw", "before")
//        );
//
//        TEST_ID = save.getId();
//    }

//    @AfterEach
//    public void teardown() {
//        sut.deleteAll();
//    }

    @Test
    @Transactional
    @Rollback(value = false)
    void run() {
        Member member1 = sut.findByEmail(TEST_EMAIl)
                .orElseThrow(RuntimeException::new);

        Member member2 = sut.findByEmail(TEST_EMAIl)
                .orElseThrow(RuntimeException::new);

        Member member3 = sut.findByEmail(TEST_EMAIl)
                .orElseThrow(RuntimeException::new);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void run2() {
        TEST_ID = 42L;
        Member member1 = sut.findById(TEST_ID)
                .orElseThrow(RuntimeException::new);

        Member member2 = sut.findById(TEST_ID)
                .orElseThrow(RuntimeException::new);

        Member member3 = sut.findById(TEST_ID)
                .orElseThrow(RuntimeException::new);
    }
}
