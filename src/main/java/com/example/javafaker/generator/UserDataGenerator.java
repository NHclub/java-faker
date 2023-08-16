package com.example.javafaker.generator;

//import com.github.javafaker.Faker;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Component;
//
//import java.io.FileWriter;
//import java.io.IOException;
////import java.sql.Date;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;


import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDataGenerator {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataGenerator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void generateCsvFile() {
        Faker faker = new Faker();
        Set<String> uniqueValues = new HashSet<>();


        int numberOfUsers = 100000; // 10만건의 데이터를 생성
        String csvFile = "users4.csv";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (FileWriter writer = new FileWriter(csvFile)) {
            // CSV 헤더 쓰기
//            writer.write("created_at,updated_at,nickname,user_account_id,password\n");


            for (int i = 1; i <= numberOfUsers; i++) {
                String nickname;
                String userAccountId;

                // nickname 중복 및 길이 확인
                do {
                    nickname = faker.name().username().replace(".", "");
                } while (uniqueValues.contains(nickname) || nickname.length() > 16);
                uniqueValues.add(nickname);

                // userAccountId 중복 및 길이 확인
                do {
                    userAccountId = faker.name().username().replace(".", "");
                } while (uniqueValues.contains(userAccountId) || userAccountId.length() > 16);
                uniqueValues.add(userAccountId);

                // password 길이 확인
                String password;
                do {
                    String rawPassword = "asdqwe123";
                    password = passwordEncoder.encode(rawPassword);
                } while (password.length() > 64);


                // createdAt과 updatedAt에 대한 타임스탬프 생성
                Date createdAt = new Date(System.currentTimeMillis() - faker.number().numberBetween(1, 30) * 24L * 60 * 60 * 1000);
                Date updatedAt = new Date(System.currentTimeMillis() - faker.number().numberBetween(1, 15) * 24L * 60 * 60 * 1000);


                // CSV에 줄 쓰기
                writer.write(createdAt.toInstant().toString() + "," +  updatedAt.toInstant().toString() + "," + nickname + "," + userAccountId + "," + password + "\n");
            }

            System.out.println("CSV 파일 생성 완료: " + csvFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}