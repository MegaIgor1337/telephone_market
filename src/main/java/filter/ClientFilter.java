package filter;

import entity.address.Address;

public record ClientFilter(int limit,
                           int offset,
                           String name,
                           String password,
                           Address address,
                           String email
                           ) {
}
