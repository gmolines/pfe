---
layout: page
title: CASPAR
longtitle: CryptogrAphie, Sécurité, et vie Privée dans les Applications et Réseaux
shortcut: true
permalink: /caspar/
---

{% for post in site.posts %}
    {% if post.categories contains 'caspar' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ site.baseurl }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



