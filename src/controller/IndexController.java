/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 *
 * @author HP
 */
public class IndexController implements Initializable {
    @FXML TextField txtInput;
    @FXML Label lblWord, lblNotification;
    @FXML Button btnOk, btnReveal, btnSkip;
    List<String> wordList = Arrays.asList("팥빙수", "남녀노수", "아버지", "삼계탕", "컴퓨터", "액정화면", "연애결혼", "백화점", "반바지", "교육원", "닭갈비", "원피스", "만화방", "전화번호", "스트레스", "샌드위치",
            "출산율", "자연스럽다", "이상하다", "나타나다", "자판기", "만리장성", "먹을거리", "세탁소", "초콜릿", "휴대전화", "요리실", "음료수", "박물관", "불고기", "종업원", "해결책", "블라우스", "목도리",
            "교수님", "배낭여행", "목적지", "역무실", "홈페이지", "낙하산", "세뱃돈", "초등학교", "경영학", "코끼리", "존댓말", "떡볶이", "해돋이", "영상통화", "인라인스케이트", "전통의상", "셔틀버스", "고추장",
            "바나나", "선풍기", "믹서기", "편의점", "마침표", "출입증", "영양분", "기차길", "할인권", "선호도", "검사실", "문구점", "단백질", "직접적", "틈틈이", "우체국", "반려동물", "피아노", "불면증", "집중력");
    //static List<String> wordList = Arrays.asList("숙제", "청소하다", "삼계탕", "아버지", "컴퓨터");
    String displayedWord, hiddenJamo, actualWord;
    int correct, skipped, currentIndex, indexHidden;
    boolean userCorrect;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNotification.setText(null);
        txtInput.setOnKeyPressed(event -> {
        lblNotification.setText(null);
            if (event.getCode().equals(KeyCode.ENTER)) {
                verifyJamo();
            }
        });
        
        Collections.shuffle(wordList);
        updateDisplayedWord();
    }
    
    
    @FXML
    public void verifyJamo(){
        if(txtInput.getText().trim().length() == 1){
            if(txtInput.getText().trim().equals(hiddenJamo)){
                userCorrect = true;
                lblWord.setText(actualWord);
                txtInput.clear();
                lblNotification.setTextFill(Color.GREEN);
                lblNotification.setText("맞습니다!");
                txtInput.setDisable(true);
                btnOk.setDisable(true);
                btnReveal.setDisable(true);
                ++correct;
                currentIndex++;
                //updateDisplayedWord();
            }else{
                lblNotification.setTextFill(Color.RED);
                lblNotification.setText("다시 해 봅시다.");
            }
        }else{
            lblNotification.setTextFill(Color.CORNFLOWERBLUE);
            lblNotification.setText("자모를 하나만 입력해 봅시다.");
        }
        
    }
    
    
    @FXML
    public void revealJamo(){
        txtInput.clear();
        txtInput.setDisable(true);
        btnOk.setDisable(true);
        btnReveal.setDisable(true);
        lblNotification.setText(null);
        lblWord.setText(actualWord);
    }
    
    
    @FXML
    public void skipWord(){
        if(!userCorrect){
            skipped++;
        }
        txtInput.clear();
        lblNotification.setText(null);
        txtInput.setDisable(false);
        btnOk.setDisable(false);
        btnReveal.setDisable(false);
        currentIndex++;
        updateDisplayedWord();
    }
    
    
    private void updateDisplayedWord(){
        userCorrect = false;
        try{
            actualWord = wordList.get(currentIndex);
            char[] wordCharacters = actualWord.toCharArray();
            indexHidden = findPositionToHide(actualWord.length());
            hiddenJamo = String.valueOf(wordCharacters[indexHidden]);
            wordCharacters[indexHidden] = '_';
            displayedWord = String.valueOf(wordCharacters);
            lblWord.setText(displayedWord);
            txtInput.requestFocus();
        }catch(Exception e){
            terminateGameSession();
            System.err.println(e.getMessage());
        }
        
    }
    
    
    private void terminateGameSession(){
        lblWord.setText(actualWord);
        lblNotification.setText("축하합니다!\n맞았습니다: " + correct + "\n건넜습니다: " + skipped);
        txtInput.setDisable(true);
        btnOk.setDisable(true);
        btnSkip.setDisable(true);
        btnReveal.setDisable(true);
    }
    
    
    private static int findPositionToHide(int wordLength){
        return (int) (Math.random() * wordLength);
    }
}
