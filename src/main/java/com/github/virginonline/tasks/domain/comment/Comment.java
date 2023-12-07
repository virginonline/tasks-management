package com.github.virginonline.tasks.domain.comment;

import com.github.virginonline.tasks.domain.AbstractEntity;
import com.github.virginonline.tasks.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment extends AbstractEntity {

  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne
  @JoinTable(name = "users_comments", inverseJoinColumns = @JoinColumn(name = "author_id"))
  private User author;
}
