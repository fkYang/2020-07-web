package com.example.demo.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.data.DataHolder;
import lombok.Data;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/4
 */
@Data
public class MarkDownUtils {
    final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL
    );

   public static final Parser PARSER = Parser.builder(OPTIONS).build();
    public static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
}
