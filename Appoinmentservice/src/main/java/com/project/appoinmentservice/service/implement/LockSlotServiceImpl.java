package com.project.appoinmentservice.service.implement;

import com.project.appoinmentservice.dto.LockSlotDTO;
import com.project.appoinmentservice.service.LockSlotService;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LockSlotServiceImpl implements LockSlotService {

    // Sử dụng ConcurrentHashMap để lưu trữ thông tin về các slot đã bị khóa
    private ConcurrentHashMap<String, LockSlotDTO> lockedSlots = new ConcurrentHashMap<>();

    // Kiểm tra xem slot có bị khóa hay không
    @Override
    public boolean isSlotLocked(LockSlotDTO lockSlotDTO) {
        String key = generateKey(lockSlotDTO);
        LockSlotDTO existingLockSlot = lockedSlots.get(key);
        System.out.println(existingLockSlot);
        return existingLockSlot == null;
    }
    @Override
    public boolean checkIfCodeExists(String randomCode) {
        for (LockSlotDTO lockSlot : lockedSlots.values()) {
            if (lockSlot.getRandomCode().equals(randomCode)) {
                return true;
            }
        }
        return false;
    }
    public boolean updateKey(LockSlotDTO lockSlotDTO) {
        String newKey = generateKey(lockSlotDTO);  // Khóa mới từ LockSlotDTO mới
        String randomCode = lockSlotDTO.getRandomCode();  // randomCode từ LockSlotDTO

        // Duyệt qua Map để tìm LockSlot có randomCode trùng
        for (ConcurrentHashMap.Entry<String, LockSlotDTO> entry : lockedSlots.entrySet()) {
            if (entry.getValue().getRandomCode().equals(randomCode)) {
                // Xóa key cũ khỏi Map (dựa trên khóa cũ)
                lockedSlots.remove(entry.getKey());
                lockedSlots.put(newKey, entry.getValue());
                return true;  // Cập nhật thành công
            }
        }

        return false;  // Không tìm thấy randomCode tương ứng
    }

    @Override
    public void getAllLockSlots() {
        for (ConcurrentHashMap.Entry<String, LockSlotDTO> entry : lockedSlots.entrySet()) {
            System.out.println(entry.getKey() + "  : " + entry.getValue().getRandomCode());
        }
    }
    // Khóa slot
    @Override
    public void lockSlot(LockSlotDTO lockSlotDTO) {
        String key = generateKey(lockSlotDTO);
        // Khóa slot trong 5 phút
        LockSlotDTO lockSlot = new LockSlotDTO();
        lockSlot.setRandomCode(lockSlotDTO.getRandomCode());
        lockedSlots.put(key, lockSlotDTO);
        for (ConcurrentHashMap.Entry<String, LockSlotDTO> entry : lockedSlots.entrySet()) {
            String key1 = entry.getKey();
            System.out.println("Key: " + key1 + ", Value: " + entry.getValue().getRandomCode());
        }

        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(5);
                unlockedSlot(lockSlotDTO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void unlockedSlot(LockSlotDTO lockSlotDTO) {
        String key = generateKey(lockSlotDTO);
        lockedSlots.remove(key);
    }

    // Tạo key duy nhất để lưu trong ConcurrentHashMap
    private String generateKey(LockSlotDTO lockSlotDTO) {
        String key = lockSlotDTO.getDoctorId().toString() + lockSlotDTO.getMedicalDay().toString()  + lockSlotDTO.getSlot().toString();
        System.out.println("key được tạo: " + key);
        return key;
    }

}
