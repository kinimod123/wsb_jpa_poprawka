package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.DoctorDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.enums.Specialization;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {

    @Override
    public List<DoctorEntity> findBySpecialization(Specialization specialization) {
        String jpql = "SELECT d FROM DoctorEntity d WHERE d.specialization = :specializationParam";
        TypedQuery<DoctorEntity> query = entityManager.createQuery(jpql, DoctorEntity.class);
        query.setParameter("specializationParam", specialization);

        return query.getResultList();
    }

    @Override
    public long countNumOfVisitsWithPatient(String docFirstName, String docLastName, String patientFirstName, String patientLastName) {
        String jpql = "SELECT COUNT(v) FROM VisitEntity v " +
                "INNER JOIN v.doctor d " +
                "INNER JOIN v.patient p " +
                "WHERE d.firstName = :doctorFirstName AND d.lastName = :doctorLastName AND " +
                "p.firstName = :patFirstName AND p.lastName = :patLastName";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("doctorFirstName", docFirstName);
        query.setParameter("doctorLastName", docLastName);
        query.setParameter("patFirstName", patientFirstName);
        query.setParameter("patLastName", patientLastName);

        return query.getSingleResult();
    }

    @Override
    public DoctorEntity findOne(Long id) {
        return entityManager.find(DoctorEntity.class, id);
    }
}
