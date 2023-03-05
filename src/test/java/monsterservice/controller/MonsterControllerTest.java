package monsterservice.controller;

import monsterservice.handleExceptionError.HandleExceptionError;
import monsterservice.model.Monster;
import monsterservice.service.MonsterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class) //คือการเรียกใช้
public class MonsterControllerTest {
    private Monster mockMonster() {
        Monster mockMonster = new Monster();
        mockMonster.setId(1);
        mockMonster.setName("ARMTEST");
        mockMonster.setHealth(10000);
        return mockMonster;
    }

    @InjectMocks
    private MonsterController monsterController;

    @Mock
    private MonsterService monsterService;

    @Test
    void getGreetingTest() {
        String response = monsterController.getGreeting();
        assertEquals("Hi there!", response);
    }

    @Test
    void postCreateTest() {
        //ให้ส่งค่าอะไรออกมา        เมือมีการเรียก
        doReturn(mockMonster()).when(monsterService).postCreateMonsterService(any(Monster.class));
        Monster response = monsterController.postCreate(new Monster());
        assertEquals(mockMonster().getId(), response.getId());
        assertEquals(mockMonster().getName(), response.getName());
        assertEquals(mockMonster().getHealth(), response.getHealth());
    }

    @Test
    void getAllMonsterTest() {
        List<Monster> monsterList = new ArrayList<>();
        monsterList.add(mockMonster());
        // getAllMonsterService ถ้าเรียกตัวนี้ให้ส่งตัว monsterList กลับไป
        doReturn(monsterList).when(monsterService).getAllMonsterService();

        List<Monster> response = monsterController.getAll();
        assertEquals(monsterList, response);
    }

    @Test
    void getInformetionTest() {
        Optional<Monster> monsterOptional = Optional.of(mockMonster());
        doReturn(monsterOptional).when(monsterService).getInformationService(any(Integer.class));

        Optional<Monster> response = monsterController.getInformation(1);

        assertTrue(response.isPresent());
        //assertFalse(response.isPresent());
    }

    @Test
    void putUpdateTestSuccess() throws HandleExceptionError {
        doReturn(mockMonster()).when(monsterService).updateMonsterByIdService(any(Monster.class));

        ResponseEntity<Monster> response = monsterController.putUpdate(new Monster());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockMonster().getId(), Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void putUpdateTestFail() throws HandleExceptionError {
        //doThrow ให้มันโยนมาที่
        doThrow(new HandleExceptionError("Error")).when(monsterService).updateMonsterByIdService(any(Monster.class));

        ResponseEntity<Monster> response = monsterController.putUpdate(new Monster());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // assertNull เช็คว่าเป็นค่าว่างหรือป่าว
        assertNull(Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void deleteTestSuccess() throws HandleExceptionError {
        doReturn(true).when(monsterService).deleteMonsterService(any(Integer.class));
        ResponseEntity<Boolean> response = monsterController.delete(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, response.getBody());
        //assertEquals(true, response.getBody());
        //assertTrue(response.getBody());
    }

    @Test
    void deleteTestFail() throws HandleExceptionError {
        doThrow(new HandleExceptionError("Error")).when(monsterService).deleteMonsterService(any(Integer.class));
        ResponseEntity<Boolean> response = monsterController.delete(1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Boolean.FALSE, response.getBody());
        //assertEquals(true, response.getBody());
        //assertTrue(response.getBody());
    }

    @Test
    void putAttackTestSuccess() throws HandleExceptionError {
        doReturn("Update Success")
                .when(monsterService).attackMonsterService(any(Integer.class), any(Integer.class));
        ResponseEntity<String> response = monsterController.putAttack(1, 100);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Update Success", response.getBody());
    }

    @Test
    void putAttackTestFailCanNotUpdate() throws HandleExceptionError {
        doThrow(new HandleExceptionError("Can't update"))
                .when(monsterService).attackMonsterService(any(Integer.class), any(Integer.class));
        ResponseEntity<String> response = monsterController.putAttack(1, 100);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Can't update", response.getBody());
    }

    @Test
    void putAttackTestFailDataNotFound() throws HandleExceptionError {
        doThrow(new HandleExceptionError("Data not found"))
                .when(monsterService).attackMonsterService(any(Integer.class), any(Integer.class));
        ResponseEntity<String> response = monsterController.putAttack(1, 100);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Data not found", response.getBody());
    }
}
