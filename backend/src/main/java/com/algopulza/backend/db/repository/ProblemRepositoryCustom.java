package com.algopulza.backend.db.repository;

import com.algopulza.backend.api.response.ProblemRes;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ProblemRepositoryCustom {

    List<ProblemRes> findProblemRes(String tierName, Integer tierLevel, Pageable pageable);

    List<ProblemRes> findProblemResByTitleLike(String keyword, Pageable pageable);

    List<Long> findAllId();

    ProblemRes findProblemResById(Long id);

    List<Long> findProblemIdByTierNameSet(Set<String> tierNameSet);

    List<Long> findProblemIdByBojTagId(int bojTagId);

    List<Long> findProblemIdByStatus(Long memberId, String status);

    List<ProblemRes> findProblemResByIdSet(Set<Long> idSet);

}
