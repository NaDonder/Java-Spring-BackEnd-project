package com.Project.CouponProject.Repositories;

import com.Project.CouponProject.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    /**
     * a Repository to find existing Company
     * @param email finding by email
     * @param password finding by password
     * @return true if found, false if not
     */
    boolean existsCompanyByEmailAndPassword(String email, String password);

    /**
     * a Repository to find Company
     * @param email finding by email
     * @param password finding by password
     * @return The Company if found one
     */
    Company findByEmailAndPassword(String email, String password);

    /**
     * a Repository to find existing Company
     * @param name finding by name
     * @param email finding by email
     * @return true if found, false if not
     */
    boolean existsCompanyByNameOrEmail(String name, String email);

    /**
     * a Repository to find company by ID
     * @param id find by ID
     * @return The Company if found one
     */
    Company findById(int id);

}
