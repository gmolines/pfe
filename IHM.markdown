---
layout: page
title: IHM
longtitle: Interactions Homme-Machines
shortcut: true
permalink: /ihm/
---

{% for post in site.posts %}
    {% if post.categories contains 'ihm' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ BASE_PATH }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



