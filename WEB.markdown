---
layout: page
title: WEB
longtitle: Sciences, Technologies, Ressources et Applications du Web
shortcut: true
permalink: /web/
---

{% for post in site.posts | sort: title %}
    {% if post.categories contains 'web' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ BASE_PATH }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



