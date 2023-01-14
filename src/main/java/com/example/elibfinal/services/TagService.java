package com.example.elibfinal.services;

import com.example.elibfinal.exception.CustomException;
import com.example.elibfinal.models.Tag;
import com.example.elibfinal.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {
private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Set<Tag> getTagsFromStringArray(String[] tags) {
        return Arrays.stream(tags)
                .map(tag -> tagRepository.findByTagName(tag).orElseThrow(() -> new CustomException("tag not found!")))
                .collect(Collectors.toSet());
    }

    public String[] getStringArrayFromTags(Set<Tag> tags) {
        return (String[]) tags.stream()
                .map(tag -> tagRepository.findById(tag.getId()).orElseThrow(() -> new CustomException("tag not found")).getTagName())
                .toArray();
    }
}
