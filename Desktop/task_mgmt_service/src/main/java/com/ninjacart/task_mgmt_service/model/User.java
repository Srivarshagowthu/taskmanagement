package com.ninjacart.task_mgmt_service.model;

import com.ninjacart.task_mgmt_service.model.AsgardUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

public class User extends UsernamePasswordAuthenticationToken {
    private final String name;
    private final AsgardUser asgardUser;
    private int id;

    public User(String name, String password, AsgardUser asgardUser, Collection<? extends GrantedAuthority> authorities) {
        super(asgardUser, password, authorities);
        this.name = name;
        this.asgardUser = asgardUser;
        if (Objects.nonNull(asgardUser)) {
            this.id = asgardUser.getId();
        }
    }


    @Override
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public AsgardUser getAsgardUser() {
        return asgardUser;
    }
}
