package com.github.virginonline.tasks.domain.user;

import com.github.virginonline.tasks.domain.AbstractEntity;
import com.github.virginonline.tasks.domain.task.Task;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

  @NotEmpty
  @Column(name = "username", unique = true)
  private String username;

  @Email
  @NotEmpty
  @Column(name = "email", unique = true)
  private String email;

  @NotEmpty
  private String password;

  @Column(name = "role")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "users_roles")
  @Enumerated(value = EnumType.STRING)
  private Set<Role> roles;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_tasks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
  private List<Task> ownedTasks;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "assigned_tasks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
  private List<Task> assignedTasks;

}
