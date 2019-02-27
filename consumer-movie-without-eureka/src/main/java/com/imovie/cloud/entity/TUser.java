package com.imovie.cloud.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "t_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TUser implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 登录名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 余额
     */
    private BigDecimal balance;

    private static final long serialVersionUID = 1L;
}