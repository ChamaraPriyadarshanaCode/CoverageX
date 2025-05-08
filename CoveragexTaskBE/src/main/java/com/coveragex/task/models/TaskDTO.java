package com.coveragex.task.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private int id;
    @NotBlank(message = "Title must not be empty")
    @Size(max = 50, message = "Title must be less than 50 characters")
    private String title;
    @NotBlank(message = "Description must not be empty")
    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
