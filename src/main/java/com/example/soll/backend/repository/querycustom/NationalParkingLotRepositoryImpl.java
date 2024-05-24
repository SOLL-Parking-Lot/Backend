package com.example.soll.backend.repository.querycustom;

import java.util.List;
import java.util.stream.Collectors;

import com.example.soll.backend.entitiy.NationalParkingLot;
import com.example.soll.backend.entitiy.QNationalParkingLot;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NationalParkingLotRepositoryImpl implements NationalParkingLotRepositoryCustom{
        private final JPAQueryFactory jpaQueryFactory;
    QNationalParkingLot nationalParkingLot = QNationalParkingLot.nationalParkingLot;

    @Override
    public List<NationalParkingLot> findTop5NationalParkingLotBy(String keyword) {
        String keywordNoSpaces = keyword.replaceAll("\\s+", "").toLowerCase();

        // Step 1: Search by parking lot name
        List<NationalParkingLot> results = searchByCondition(nationalParkingLot.parkingLotName, keyword, keywordNoSpaces, 5);

        // Step 2: If less than 5 results found, search by road name address to fill up to 5 results
        if (results.size() < 5) {
            List<NationalParkingLot> roadNameResults = searchByCondition(nationalParkingLot.roadNameAddress, keyword, keywordNoSpaces, 5 - results.size());
            results.addAll(roadNameResults);
        }

        // Step 3: If still less than 5 results found, search by lot number address to fill up to 5 results
        if (results.size() < 5) {
            List<NationalParkingLot> lotNumberResults = searchByCondition(nationalParkingLot.lotNumberAddress, keyword, keywordNoSpaces, 5 - results.size());
            results.addAll(lotNumberResults);
        }

        // Step 4: Filter results to include only those containing the entire keyword ignoring spaces
        List<NationalParkingLot> filteredResults = results.stream()
                .filter(parkingLot -> containsWholeKeywordIgnoringSpaces(parkingLot, keywordNoSpaces))
                .limit(5)
                .collect(Collectors.toList());

        return filteredResults;
    }

    private List<NationalParkingLot> searchByCondition(StringPath field, String keyword, String keywordNoSpaces, int limit) {
        BooleanExpression condition = field.containsIgnoreCase(keyword)
                                    .or(removeSpaces(field).containsIgnoreCase(keywordNoSpaces));
        return jpaQueryFactory.selectFrom(nationalParkingLot)
                .where(condition)
                .orderBy(
                    nationalParkingLot.parkingLotName.asc(),
                    nationalParkingLot.roadNameAddress.asc(),
                    nationalParkingLot.lotNumberAddress.asc()
                )
                .limit(limit)
                .fetch();
    }

    private StringTemplate removeSpaces(StringPath field) {
        // 공백 제거 후 소문자로 변환하여 검색 조건에 사용할 수 있도록 쿼리 조작
        // 예시로 아래와 같이 구현합니다.
        // DB 함수가 필요한 경우 실제 DB에 맞는 함수로 변경해야 합니다.
        return Expressions.stringTemplate("lower(replace({0}, ' ', ''))", field);
    }

    private boolean containsWholeKeywordIgnoringSpaces(NationalParkingLot parkingLot, String keywordNoSpaces) {
        String nameNoSpaces = parkingLot.getParkingLotName().replaceAll("\\s+", "").toLowerCase();
        String roadNameAddressNoSpaces = parkingLot.getRoadNameAddress().replaceAll("\\s+", "").toLowerCase();
        String lotNumberAddressNoSpaces = parkingLot.getLotNumberAddress().replaceAll("\\s+", "").toLowerCase();
        return nameNoSpaces.contains(keywordNoSpaces) || roadNameAddressNoSpaces.contains(keywordNoSpaces) || lotNumberAddressNoSpaces.contains(keywordNoSpaces);
    }
}