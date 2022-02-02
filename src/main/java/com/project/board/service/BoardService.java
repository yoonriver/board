package com.project.board.service;

import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.repository.UserRepository;
import com.project.board.repository.WriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final WriteRepository writeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Page<WriteEntity> 글목록(int pageNum, String option, String keyword, String category) {

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by("createDate").descending());

        if(category == null || category.isEmpty()) {
            if(keyword == null || keyword.isEmpty()) {
                Page<WriteEntity> writeList = writeRepository.findAll(pageable);

                return writeList;

            }else {
                switch (option) {
                    case "titleContent":
                        Page<WriteEntity> byTitleOrContentContaining = writeRepository.findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword, pageable);
                        return byTitleOrContentContaining;
                    case "title":
                        Page<WriteEntity> byTitleContaining = writeRepository.findByTitleContainingIgnoreCase(keyword, pageable);
                        return byTitleContaining;
                    case "content":
                        Page<WriteEntity> byContentContaining = writeRepository.findByContentContainingIgnoreCase(keyword, pageable);
                        return byContentContaining;
                    case "name":
                        Page<WriteEntity> byUserEntity_nameContaining = writeRepository.findByUserEntity_NameContaining(keyword, pageable);
                        return byUserEntity_nameContaining;
                    default:
                        Page<WriteEntity> all = writeRepository.findAll(pageable);
                        return all;
                }
            }

        }else {
            if(keyword == null || keyword.isEmpty()) {
                Page<WriteEntity> writeList = writeRepository.findByCategoryContainingIgnoreCase(category, pageable);

                return writeList;

            }else {
                switch (option) {
                    case "titleContent":
                        Page<WriteEntity> byTitleOrContentContaining = writeRepository.findByContentContainingIgnoreCaseAndCategoryContainingOrTitleContainingIgnoreCaseAndCategoryContaining(category, keyword, pageable);
                        return byTitleOrContentContaining;
                    case "title":
                        Page<WriteEntity> byTitleContaining = writeRepository.findByTitleContainingIgnoreCaseAndCategoryContaining(category, keyword, pageable);
                        return byTitleContaining;
                    case "content":
                        Page<WriteEntity> byContentContaining = writeRepository.findByContentContainingIgnoreCaseAndCategoryContaining(category, keyword, pageable);
                        return byContentContaining;
                    default:
                        Page<WriteEntity> all = writeRepository.findByCategoryContainingIgnoreCase(category, pageable);
                        return all;
                }
            }
        }
    }

    @Transactional
    public List<WriteEntity> 공지목록() {
        List<WriteEntity> noticeList = writeRepository.findByCategoryContainingOrderByCreateDateAsc("공지");
        return noticeList;

    }

    @Transactional
    public Page<WriteEntity> 내글목록(int pageNum, String option, String keyword, Long userId) {

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by("createDate").descending());

        if(keyword == null || keyword.isEmpty()) {
            Page<WriteEntity> writeList = writeRepository.findByUserIdContaining(userId, pageable);

            return writeList;

        }else {
            switch (option) {
                case "titleContent":
                    Page<WriteEntity> byTitleOrContentContaining = writeRepository.findByUserContainingIgnoreCaseOrTitleContainingIgnoreCaseAndUserIdContaining(userId, keyword, pageable);
                    return byTitleOrContentContaining;
                case "title":
                    Page<WriteEntity> byTitleContaining = writeRepository.findByTitleContainingIgnoreCaseAndUserIdContaining(userId, keyword, pageable);
                    return byTitleContaining;
                case "content":
                    Page<WriteEntity> byContentContaining = writeRepository.findByContentContainingIgnoreCaseAndUserIdContaining(userId, keyword, pageable);
                    return byContentContaining;
                default:
                    Page<WriteEntity> all = writeRepository.findAll(pageable);
                    return all;
            }
        }
    }
}
