---
layout: page
title: IAM
longtitle: Intelligence Ambiante
shortcut: true
permalink: /iam/
---

{% for post in site.posts %}
    {% if post.categories contains 'iam' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ BASE_PATH }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



