package com.gmail.erofeev.st.alexei.myonlineshop.service.converter;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;

public class ProfileConverter {

    public static ProfileDTO toDTO(Profile profile) {
        String address = profile.getAddress();
        String telephone = profile.getTelephone();
        Long id = profile.getUser().getId();
        return new ProfileDTO(id, address, telephone);
    }
    public static Profile fromDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setAddress(profileDTO.getAddress());
        profile.setTelephone(profileDTO.getTelephone());
        User user = new User();
        user.setId(profileDTO.getId());
        profile.setUser(user);
        return profile;
    }
}
