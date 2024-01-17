package com.green.greengram4.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Getter
@Component
public class MyFileUtils {
    private final String uploadPrefixPath;

    public MyFileUtils(@Value("${file.dir}")String uploadPrefixPath) {
        this.uploadPrefixPath = uploadPrefixPath;
    }

    //폴더 만들기
    public String makeFolders(String parh) {
        File folder = new File(uploadPrefixPath, parh);
        folder.mkdirs();
        return folder.getAbsolutePath();
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm() {
        return UUID.randomUUID().toString();
    }

    //확장자 얻어오기
    public String getExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf("."));
    }

    //랜덤 파일명 만들기 with 확장자
    public String getRandomFileNm(String originFileNm) {
        return getRandomFileNm() + getExt(originFileNm);
    }

    //랜덤 파일명 만들기 with 확장자 from MultipartFile
    public String getRandomFileNm(MultipartFile mf) {
        String fileNm = mf.getOriginalFilename();
        return getRandomFileNm(fileNm);
    }

    //메모리에 있는 내용 > 파일로 옮기는 메소드
    public String transferTo(MultipartFile mf, String target) {
        String fileNm = getRandomFileNm(mf);
        String folderPath = makeFolders(target);
        File saveFile = new File(folderPath, fileNm);

        try {
            mf.transferTo(saveFile); // 메모리내용을 transferTo 이용해 파일로 옮겨줌
            return fileNm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delFolderTrigger(String relativePath){
        delFolder(uploadPrefixPath + relativePath);
    }

    public void delFolder(String folderPath) { //폴더 아래에 폴더 및 파일 삭제, 보냈는 폴더는 삭제 안함
        File folder = new File(folderPath);
        if(folder.exists()) {
            File[] files = folder.listFiles();

            for(File file : files) {
                if(file.isDirectory()) {
                    delFolder(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
            folder.delete();
        }
    }

}
