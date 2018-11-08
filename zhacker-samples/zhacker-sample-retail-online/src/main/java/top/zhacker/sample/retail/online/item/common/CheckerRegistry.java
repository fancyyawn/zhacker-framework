package top.zhacker.sample.retail.online.item.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.Valid;

import lombok.Setter;


@Validated
public abstract class CheckerRegistry<T> {
  
  @Autowired
  @Setter
  private List<Checker<T>> checkers;
  
  public void check(@Valid T entity, Operation operation){
    checkers.stream().filter(s-> s.support().contains(operation)).forEach(spec-> spec.check(entity));
  }
}
