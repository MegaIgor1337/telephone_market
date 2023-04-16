package dao.filter;

import entity.address.Address;
import entity.user.Gender;
import entity.user.Role;

public record UserFilter(int limit,
                         int offset,
                         String name,
                         String password,
                         String email,
                         Role role,
                         Gender gender
                           ) {
}
