package com.project.board.service;

import com.project.board.entity.WriteEntity;
import com.project.board.repository.WriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final WriteRepository writeRepository;

    @Transactional
    public Page<WriteEntity> 글목록(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by("createDate").descending());
        Page<WriteEntity> writeList = writeRepository.findAll(pageable);

        return writeList;
    }

    @Transactional
    public Page<WriteEntity> 검색(int pageNum, String option, String keyword) {
        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by("createDate").descending());
        switch (option) {
            case "titleContent" :
                Page<WriteEntity> byTitleOrContentContaining = writeRepository.findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword, pageable);
                return byTitleOrContentContaining;
            case "title" :
                Page<WriteEntity> byTitleContaining = writeRepository.findByTitleContainingIgnoreCase(keyword, pageable);
                return byTitleContaining;
            case "content" :
                Page<WriteEntity> byContentContaining = writeRepository.findByContentContainingIgnoreCase(keyword, pageable);
                return byContentContaining;
            case "category" :
                Page<WriteEntity> byCategoryContainingIgnoreCase = writeRepository.findByCategoryContainingIgnoreCase(keyword, pageable);
                return byCategoryContainingIgnoreCase;
            default :
                Page<WriteEntity> all = writeRepository.findAll(pageable);
                return all;
        }
    }
}
