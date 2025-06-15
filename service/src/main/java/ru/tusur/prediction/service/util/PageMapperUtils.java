package ru.tusur.prediction.service.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ru.tusur.prediction.service.api.data.dto.PageDto;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PageMapperUtils {
    public static <S, D> PageDto<D> mapToPage(
            List<S> items,
            int page,
            int size,
            Function<S, D> mapper
    ) {
        int totalElements = items.size();

        int fromIndex = page * size;
        int toIndex = Math.min(items.size(), fromIndex + size);

        List<D> content = fromIndex > toIndex
                ? Collections.emptyList()
                : items.subList(fromIndex, toIndex).stream()
                .map(mapper)
                .toList();

        return new PageDto<>(
                content,
                page,
                size,
                totalElements,
                (int) Math.ceil((double) totalElements / size)
        );
    }
}
