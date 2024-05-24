package com.wallace.deliverychallenge.application.fixture;

import com.wallace.deliverychallenge.domain.model.Partner;
import com.wallace.deliverychallenge.utils.DataLoader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PartnerFixture {
    private DataLoader dataLoader;

    public PartnerFixture(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<Partner> createPartners() throws Exception {
        List<Partner> partners = dataLoader.loadData();

        for (Partner partner : partners) {
            partner.setId(UUID.randomUUID());
        }

        return partners;
    }
}
