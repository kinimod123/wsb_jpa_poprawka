package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public PatientEntity findOne(Long id) {
        return entityManager.find(PatientEntity.class, id);
    }

    @Override
    public List<PatientEntity> findByDoctor(String firstName, String lastName) {
        String jpql = "SELECT p FROM PatientEntity p " +
                "INNER JOIN p.visits v " +
                "WHERE v.doctor.firstName = :docFirstName AND v.doctor.lastName = :docLastName";

        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("docFirstName", firstName);
        query.setParameter("docLastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatmentType) {
        String jpql = "SELECT DISTINCT p FROM PatientEntity p " +
                "INNER JOIN p.visits v " +
                "INNER JOIN v.medicalTreatments t " +
                "WHERE t.type = :treatType";

        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("treatType", treatmentType);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String firstName, String lastName) {
        String jpql = "SELECT DISTINCT p FROM PatientEntity p " +
                "INNER JOIN p.addresses pa " +
                "INNER JOIN pa.doctors da " +
                "WHERE da.firstName = :docFirstName AND da.lastName = :docLastName";

        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("docFirstName", firstName);
        query.setParameter("docLastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithoutLocation() {
        String jpql = "SELECT p FROM PatientEntity p WHERE p.addresses IS EMPTY";

        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        return query.getResultList();
    }
}
