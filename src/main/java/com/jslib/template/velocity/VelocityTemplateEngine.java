package com.jslib.template.velocity;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import com.jslib.api.template.Template;
import com.jslib.api.template.TemplateEngine;

public class VelocityTemplateEngine implements TemplateEngine
{
  @Override
  public void setProperty(String name, Object value)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public Template getTemplate(String targetName, Reader reader) throws IOException
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Template getTemplate(File templateFile) throws IOException
  {
    // TODO Auto-generated method stub
    return null;
  }
}
