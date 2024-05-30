package com.example.soll.backend.repository.querycustom;

import com.example.soll.backend.entitiy.QSeoulParkingLot;
import com.example.soll.backend.entitiy.SeoulParkingLot;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SeoulParkingLotRepositoryCustomImpl implements SeoulParkingLotRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    QSeoulParkingLot seoulParkingLot = QSeoulParkingLot.seoulParkingLot;


    @Override
    public List<SeoulParkingLot> findTop5SeoulParkingLotBy(String keyword) {
        String keywordNoSpaces = keyword.replaceAll("\\s+", "").toLowerCase();

        // Step 1: Search by parking lot name
        List<SeoulParkingLot> results = searchByCondition(seoulParkingLot.parkingLotName, keyword, keywordNoSpaces, 5);

        // Step 2: If less than 5 results found, search by address to fill up to 5 results
        if (results.size() < 5) {
            List<SeoulParkingLot> addressResults = searchByCondition(seoulParkingLot.address, keyword, keywordNoSpaces, 5 - results.size());
            results.addAll(addressResults);
        }

        // Step 3: Filter results to include only those containing the entire keyword ignoring spaces
        List<SeoulParkingLot> filteredResults = results.stream()
                .filter(parkingLot -> containsWholeKeywordIgnoringSpaces(parkingLot, keywordNoSpaces))
                .limit(5)
                .collect(Collectors.toList());

        return filteredResults;
    }

    private List<SeoulParkingLot> searchByCondition(StringPath field, String keyword, String keywordNoSpaces, int limit) {
        BooleanExpression condition = field.containsIgnoreCase(keyword)
                                    .or(removeSpaces(field).containsIgnoreCase(keywordNoSpaces));
        return jpaQueryFactory.selectFrom(seoulParkingLot)
                .where(condition)
                .orderBy(seoulParkingLot.parkingLotName.asc(), seoulParkingLot.address.asc())
                .limit(limit)
                .fetch();
    }

    private StringTemplate removeSpaces(StringPath field) {
        return Expressions.stringTemplate("lower(replace({0}, ' ', ''))", field);
    }

    private boolean containsWholeKeywordIgnoringSpaces(SeoulParkingLot parkingLot, String keywordNoSpaces) {
        String nameNoSpaces = parkingLot.getParkingLotName().replaceAll("\\s+", "").toLowerCase();
        String addressNoSpaces = parkingLot.getAddress().replaceAll("\\s+", "").toLowerCase();
        return nameNoSpaces.contains(keywordNoSpaces) || addressNoSpaces.contains(keywordNoSpaces);
    }
}