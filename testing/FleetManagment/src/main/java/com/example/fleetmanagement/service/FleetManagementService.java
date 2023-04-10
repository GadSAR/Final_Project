package com.example.fleetmanagement.service;

import com.example.fleetmanagement.entity.Info;
import com.example.fleetmanagement.repository.FleetManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FleetManagementService implements FleetManagementServiceInterface {

    // upload folder for images, it is defined in application.properties
    @Value("${uploadDir}")
    private String uploadFolder;

    FleetManagementRepository fleetManagementRepository;

    @Autowired
    public FleetManagementService(FleetManagementRepository fleetManagementRepository) {
        this.fleetManagementRepository = fleetManagementRepository;
    }

    @Override
    public List<Info> getAllStudents() {

        /*
        now create image files in static/images/ directory,
        the file name is composed of filePath+student.getId()+".jpg"
        and, this is used for the html file allInfo.html
        for showing the image according to the student id
        */

        return fleetManagementRepository.findAll();
    }

/*
    @Override
    public Info saveStudent(Info info, MultipartFile file) {
        // take the file and convert it to bytes array
        FleetManagmentService.makeBlobForDatabase(info, file);
        info.setImageFileURL(file.getOriginalFilename());
        return fleetManagmentRepository.save(info);
    }

    @Override
    public Info updateStudent(Long id, Info info, MultipartFile file, boolean isANewImageFile)
    {
        Info getInfoFromDatabase = getStudentById(id);
        getInfoFromDatabase.setId(info.getId());
        getInfoFromDatabase.setFirstName(info.getFirstName());
        getInfoFromDatabase.setLastName(info.getLastName());
        getInfoFromDatabase.setEmail(info.getEmail());
        getInfoFromDatabase.setBirthDay(info.getBirthDay());
        getInfoFromDatabase.setGender(info.getGender());

        // if a new image file is selected then,
        if (isANewImageFile) {
            getInfoFromDatabase.setImageFileURL(file.getOriginalFilename());
            // take the file and convert it to bytes array, for blob
            makeBlobForDatabase(getInfoFromDatabase, file);
        }
        else {
            // otherwise use the original image file
             getInfoFromDatabase.setImage(getStudentById(info.getId()).getImage());
             getInfoFromDatabase.setImageFileURL(getStudentById(info.getId()).getImageFileURL());
        }

        return fleetManagmentRepository.save(getInfoFromDatabase);
    }

   @Override
    public Info getStudentById(Long id) {
        return fleetManagmentRepository.findById(id).get();
    }


    // make blob for database from image file
    private static void makeBlobForDatabase(Info info, MultipartFile file) {
        byte[] imageData;

        // convert the file to bytes
        try {
            imageData = file.getBytes();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // create blob image for database from bytes
        Blob blob;
        try {
            blob = new SerialBlob(imageData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // save the blob in student
        info.setImage(blob);
    }

    @Override
    public void deleteStudent(Long id) {
        fleetManagementRepository.deleteById(id);
    }
 */

}
