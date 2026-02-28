package com.yahiaprince.store.setup;

import com.yahiaprince.store.users.Role;

import jakarta.validation.constraints.NotNull;

public record ChangeUserRoleRequest(@NotNull Role role) {
}