package dao.filter;

import entity.user.User;

public record AddressFilter(int limit,
                            int offset,
                            String country,
                            String city,
                            String street,
                            String house,
                            String flat,
                            User user) {
}
