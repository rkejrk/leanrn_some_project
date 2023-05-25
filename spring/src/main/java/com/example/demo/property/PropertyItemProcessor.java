package com.example.demo.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

/**
 * 中間プロセッサー
 * 文字をアッパークラスにしてくれる機能
 * ItemProcessorを継承することによってバッチジョブにコードを簡単に接続できます。
 */
public class PropertyItemProcessor implements ItemProcessor<Property, Property> {

  private static final Logger log = LoggerFactory.getLogger(PropertyItemProcessor.class);

  @Override
  public Property process(@NonNull final Property property) throws Exception {
    final String name = property.getName().toUpperCase();
    final String image = property.getImage().toUpperCase();
    final String address = property.getAddress().toUpperCase();

    final Property transformedProperty = new Property(name, image, address);

    log.info("Converting (" + property + ") into (" + transformedProperty + ")");

    return transformedProperty;
  }

}