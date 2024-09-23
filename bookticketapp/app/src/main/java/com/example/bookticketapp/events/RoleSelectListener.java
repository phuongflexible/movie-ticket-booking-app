package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Role;

public interface RoleSelectListener {
    public void updateRole(Role role);
    public void deleteRole(Role role);
}
