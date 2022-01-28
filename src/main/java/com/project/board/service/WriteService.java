package com.project.board.service;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.WriteDto;
import com.project.board.entity.FileEntity;
import com.project.board.entity.LikesEntity;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomStandardValidationException;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.UploadRepository;
import com.project.board.repository.WriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WriteService {

    private final WriteRepository writeRepository;
    private final UploadRepository uploadRepository;

    @Value("${file.path}") // application.yml에서 설정
    private String uploadFolder;

    @Transactional
    public WriteEntity 글쓰기(WriteDto writeDto, UserEntity userEntity) {

        WriteEntity writeEntity = writeDto.toEntity(userEntity, 0);
        writeRepository.save(writeEntity);

        // 파일을 선택하지 않았을 경우 들어오는 공백값 확인 후 fileList를 null로 초기화
        if(writeDto.getFileList().get(0).getOriginalFilename().trim().length() == 0) {
            writeDto.setFileList(null);
        }

        if(!CollectionUtils.isEmpty(writeDto.getFileList())) {

            if(writeDto.getFileList().size() > 10) {
                throw new CustomStandardValidationException("파일을 10개 이상 등록 할 수 없습니다.");
            }

            for (MultipartFile multipartFile : writeDto.getFileList()) {

                if(multipartFile.getSize() > 10485760 ) {
                    throw new MaxUploadSizeExceededException(multipartFile.getSize());
                }

                if(multipartFile.getOriginalFilename().contains(".png") || multipartFile.getOriginalFilename().contains(".jpg") || multipartFile.getOriginalFilename().contains(".PNG") || multipartFile.getOriginalFilename().contains(".JPG")) {

                    UUID uuid = UUID.randomUUID();

                    String imageFileName = uuid + "_" + multipartFile.getOriginalFilename();

                    System.out.println("이미지 파일 이름 " + imageFileName);

                    Path imageFilePath = Paths.get(uploadFolder + imageFileName);

                    try {
                        Files.write(imageFilePath, multipartFile.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    FileEntity file = FileEntity.builder()
                            .imageFileName(imageFileName)
                            .writeEntity(writeEntity)
                            .build();

                    uploadRepository.save(file);

                }else {
                    throw new CustomStandardValidationException("jpg 혹은 png 파일만 업로드 가능합니다.");
                }
            }

            WriteEntity findWrites = writeRepository.findById(writeEntity.getId()).get();

            return findWrites;

        }else {

            WriteEntity findWrites = writeRepository.findById(writeEntity.getId()).get();

            return findWrites;
        }

    }

    @Transactional
    public void 글수정(Long writeId, WriteDto writeDto, UserEntity userEntity) {

        WriteEntity findWrites = writeRepository.findById(writeId).orElseThrow(() -> {
            throw new CustomValidationApiException("게시글이 없습니다.");
        });

        if(findWrites.getUserEntity().getId() != userEntity.getId()) {
            throw new CustomStandardValidationException("권한이 없습니다.");
        }

        if(userEntity.getId() == findWrites.getUserEntity().getId()) {
            // 첨부파일 수정
            if(!CollectionUtils.isEmpty(writeDto.getDeleteImageNames())) {

                for (String deleteImageName : writeDto.getDeleteImageNames()) {
                    FileEntity findImageFile = uploadRepository.findByImageFileName(deleteImageName);
                    System.out.println("findImageFile.getId() = " + findImageFile.getId());
                    uploadRepository.deleteById(findImageFile.getId());

                    if(findImageFile != null ) {
                        String path = uploadFolder + deleteImageName; // 삭제 할 파일의 경로
                        System.out.println("path = " + path);
                        File file = new File(path);
                        if (file.exists()) {
                            file.delete();

                        }
                        uploadRepository.flush();
                    }
                }
            }

            findWrites = writeRepository.findById(writeId).orElseThrow(() -> {
                throw new CustomValidationApiException("게시글이 없습니다.");
            });

            // 글 수정
            findWrites.setTitle(writeDto.getTitle());
            findWrites.setCategory(writeDto.getCategory());
            findWrites.setContent(writeDto.getContent());

            // 파일을 선택하지 않았을 경우 들어오는 공백값 확인 후 fileList를 null로 초기화
            if(writeDto.getFileList().get(0).getOriginalFilename().trim().length() == 0) {
                writeDto.setFileList(null);
            }

            // 첨부파일 추가
            if(!CollectionUtils.isEmpty(writeDto.getFileList())) {
                if(writeDto.getFileList().size() > 10) {
                    throw new CustomStandardValidationException("파일을 10개 이상 등록 할 수 없습니다.");
                }

                for (MultipartFile multipartFile : writeDto.getFileList()) {
                    if(multipartFile.getSize() > 104857600 ) {
                        throw new MaxUploadSizeExceededException(multipartFile.getSize());
                    }

                    if(multipartFile.getOriginalFilename().contains(".png") || multipartFile.getOriginalFilename().contains(".jpg") || multipartFile.getOriginalFilename().contains(".PNG") || multipartFile.getOriginalFilename().contains(".JPG")) {

                        UUID uuid = UUID.randomUUID();

                        String imageFileName = uuid + "_" + multipartFile.getOriginalFilename();

                        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

                        try {
                            Files.write(imageFilePath, multipartFile.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        FileEntity file = FileEntity.builder()
                                .imageFileName(imageFileName)
                                .writeEntity(findWrites)
                                .build();

                        uploadRepository.save(file);
                        uploadRepository.flush();

                    }else {
                        throw new CustomStandardValidationException("jpg 혹은 png 파일만 업로드 가능합니다.");
                    }
                }

                // 추가 된 이미지 목록이 10개 이상인지 검사
                findWrites = writeRepository.findById(writeId).orElseThrow(() -> {
                    throw new CustomValidationApiException("게시글이 없습니다.");
                });

                if(findWrites.getFiles().size() > 10) {
                    throw new CustomStandardValidationException("파일을 10개 이상 등록 할 수 없습니다.");
                }

            }
        }else {
            throw new CustomUpdateValidationException("수정 할 권한이 없습니다.");
        }

    }

    @Transactional
    public WriteEntity 글보기(Long writeId, PrincipalDetails principalDetails) {

        WriteEntity writeEntity = writeRepository.findById(writeId).orElseThrow(() -> {
            return new CustomValidationException("존재하지 않는 게시글 입니다.");
        });

        // 글 쓴 본인이 아니면 조회수+1
        if(writeEntity.getUserEntity().getId() != principalDetails.getUserEntity().getId()) {
            writeEntity.setCount(writeEntity.getCount() + 1);
        }

        // 추천 상태 확인
        List<LikesEntity> likes = writeEntity.getLikes();
        for (LikesEntity like : likes) {
            if(like.getUserEntity().getId() == principalDetails.getUserEntity().getId()) {
                writeEntity.setIsLikes(1);
            }
        }

        return writeEntity;
    }
    
    @Transactional
    public void 글삭제(Long writeId, UserEntity userEntity) {

        WriteEntity findWrites = writeRepository.findById(writeId).orElseThrow(() -> {
            return new CustomValidationApiException("게시글이 없습니다.");
        });

        if(findWrites.getUserEntity().getId() != userEntity.getId()) {
            throw new CustomStandardValidationException("권한이 없습니다.");
        }

        if(userEntity.getId() == findWrites.getUserEntity().getId()) {

            // 파일 삭제
            if(findWrites.getFiles() != null) {
                for (FileEntity writeFile : findWrites.getFiles()) {
                    String path = uploadFolder + writeFile.getImageFileName(); // 삭제 할 파일의 경로

                    File file = new File(path);

                    if(file.exists() == true){

                        file.delete();
                    }
                }
            }

            writeRepository.deleteById(writeId);

        }else {
            throw new CustomUpdateValidationException("삭제 할 권한이 없습니다.");
        }
    }
}
