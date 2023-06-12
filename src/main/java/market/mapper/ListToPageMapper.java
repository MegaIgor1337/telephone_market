package market.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListToPageMapper {
    public  <T> Page<T> convertListToPage(List<T> list, PageRequest pageRequest) {
        int startIndex = pageRequest.getPageNumber() * pageRequest.getPageSize();
        int endIndex = Math.min(startIndex + pageRequest.getPageSize(), list.size());
        List<T> sublist = list.subList(startIndex, endIndex);
        return new PageImpl<>(sublist, PageRequest.of(pageRequest.getPageNumber(),
                pageRequest.getPageSize()), list.size());
    }
}
