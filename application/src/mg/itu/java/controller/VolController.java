package mg.itu.java.controller;
import framework.ModelView;
import framework.Annotation.Url;
import framework.Annotation.Auth;
import framework.Annotation.Param;
import framework.Annotation.Controller;
import mg.itu.java.database.Connexion;
import mg.itu.java.model.Avion;
import mg.itu.java.model.Ville;
import mg.itu.java.model.Vol_siege;
import mg.itu.java.model.Type_siege;
import mg.itu.java.model.Vol;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import framework.Annotation.Post;

@Controller
public class VolController {
    @Url("/vol_list")
    public ModelView list() {
        ModelView modelview = new ModelView("./page/vol/vol.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("villeList", Ville.getAll(conn));
            modelview.add("avionList", Avion.getAll(conn));
            modelview.add("volList", Vol.getAll(conn));
            modelview.add("volSiegeList", Vol_siege.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }

    @Url("/vol_form")
    @Auth("admin")
    public ModelView form() {
        ModelView modelview = new ModelView("./page/vol/formulaireVol.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("villeList", Ville.getAll(conn));
            modelview.add("avionList", Avion.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }

    @Url("/vol_insert")
    @Post
    @Auth("admin")
    public ModelView insert(@Param("vol") Vol vol, @Param("vol_siege") Vol_siege[] volSieges) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
            conn.setAutoCommit(false);

            vol.insert(conn);

            if (volSieges != null) {  
                for (Vol_siege siege : volSieges) {
                    if (siege != null) {  
                        siege.setVol(vol);
                        siege.insert(conn);
                    }
                }
            }

            conn.commit();

            modelview.add("volList", Vol.getAll(conn));
            modelview.add("villeList", Ville.getAll(conn));
            modelview.add("avionList", Avion.getAll(conn));
            modelview.add("volSiegeList", Vol_siege.getAll(conn));
            modelview.setUrl("./page/vol/vol.jsp");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            modelview.setUrl("./page/vol/formulaireVol.jsp");
            modelview.add("error", "Une erreur est survenue lors de l'insertion");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }

    @Url("/vol_delete")
    @Auth("admin")
    public ModelView delete(@Param("id_vol") String id_vol) {
        ModelView modelview = new ModelView("./page/vol/vol.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            conn.setAutoCommit(false);
            
            List<Vol_siege> sieges = Vol_siege.getByVolId(id_vol, conn);
            for (Vol_siege siege : sieges) {
                Vol_siege.delete(siege.getType_siege().getId_type_siege(), conn);
            }
            
            Vol.delete(id_vol, conn);
            
            conn.commit();
            
            modelview.add("villeList", Ville.getAll(conn));
            modelview.add("avionList", Avion.getAll(conn));
            modelview.add("volList", Vol.getAll(conn));
            modelview.add("volSiegeList", Vol_siege.getAll(conn));
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }

    @Url("/vol_update")
    @Auth("admin")
    public ModelView update(@Param("id_vol") String id_vol) {
        ModelView modelview = new ModelView("./page/vol/formulaireVol.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("villeList", Ville.getAll(conn));
            modelview.add("avionList", Avion.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
            modelview.add("vol",Vol.getById(id_vol, conn));
            modelview.add("vol_siege",Vol_siege.getByVolId(id_vol, conn));
        } catch (Exception e) {
            
        }finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return modelview;
    }

    @Url("/vol_store")
    @Post
    @Auth("admin")
    public ModelView store(@Param("vol") Vol vol, @Param("vol_siege") Vol_siege[] volSieges) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
            conn.setAutoCommit(false);

            vol.update(conn);

            if (volSieges != null) {  
                for (Vol_siege siege : volSieges) {
                    if (siege != null) {  
                        siege.setVol(vol);
                        siege.update(conn);
                    }
                }
            }

            conn.commit();

            modelview.add("volList", Vol.getAll(conn));
            modelview.add("villeList", Ville.getAll(conn));
            modelview.add("avionList", Avion.getAll(conn));
            modelview.add("volSiegeList", Vol_siege.getAll(conn));
            modelview.setUrl("./page/vol/vol.jsp");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            modelview.setUrl("./page/vol/formulaireVol.jsp");
            modelview.add("error", "Une erreur est survenue lors de l'insertion");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;

    }

    @Url("/vol_search")
    public ModelView search(@Param("dateDebut")LocalDateTime dateDebut,@Param("dateFin")LocalDateTime dateFin, 
    @Param("id_ville")String idVille,@Param("id_avion") String idAvion) {
        ModelView modelview = new ModelView("./page/vol/vol.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("villeList", Ville.getAll(conn));
            modelview.add("avionList", Avion.getAll(conn));
            modelview.add("volSiegeList", Vol_siege.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
            modelview.add("volList",Vol.multiCritere(conn, dateDebut, dateFin, idVille, idAvion));
        } catch (Exception e) {
            
        }finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return modelview;
    }
}