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
}
